import { useEffect, useState } from "react";
import axios from "axios";

function PurchaseEntry({ onSuccess }) {
  const [vendors, setVendors] = useState([]);
  const [categories, setCategories] = useState([]);
  const [types, setTypes] = useState([]);
  const [units, setUnits] = useState([]);

  const [selectedCategory, setSelectedCategory] = useState("");
  const [errorMessage, setErrorMessage] = useState("");

  useEffect(() => {
    axios
      .get("http://localhost:8089/vendors")
      .then((response) => {
        setVendors(response.data);
      })
      .catch((error) => {
        console.error("Error loading vendors:", error);
        setErrorMessage("Unable to load vendor details.");
      });
  }, []);

  useEffect(() => {
    axios
      .get("http://localhost:8089/categories")
      .then((response) => {
        setCategories(response.data);
      })
      .catch((error) => {
        console.error("Error loading categories:", error);
        setErrorMessage("Unable to load material categories.");
      });
  }, []);

  const handleCategoryChange = (event) => {
    const categoryId = event.target.value;

    setSelectedCategory(categoryId);
    setTypes([]);
    setUnits([]);
    setErrorMessage("");

    if (categoryId === "") {
      return;
    }

    axios
      .post("http://localhost:8089/getUnitAndTypeList", {
        materialCategoryId: categoryId
      })
      .then((response) => {
        setUnits(response.data.unitList || []);
        setTypes(response.data.materialTypeList || []);
      })
      .catch((error) => {
        console.error(
          "Error loading material type and unit:",
          error
        );

        setErrorMessage(
          "Unable to load material type and unit."
        );
      });
  };

  const handleSubmit = async (event) => {
    event.preventDefault();

    setErrorMessage("");

    const form = event.target;

    const vendorName = form.vendorName.value;
    const materialCategoryId = form.materialCategoryId.value;
    const materialTypeId = form.materialTypeId.value;
    const unitId = form.unitId.value;
    const brandName = form.brandName.value;
    const quantity = Number(form.quantity.value);
    const purchaseAmount = Number(form.purchaseAmount.value);
    const purchaseDate = form.purchaseDate.value;

    const today = new Date();

    const year = today.getFullYear();
    const month = String(today.getMonth() + 1).padStart(2, "0");
    const day = String(today.getDate()).padStart(2, "0");

    const todayDate = `${year}-${month}-${day}`;

    if (purchaseDate >= todayDate) {
      setErrorMessage(
        "Purchase date must be before today."
      );
      return;
    }

    const selectedCategoryObject = categories.find(
      (category) =>
        category.categoryId === materialCategoryId
    );

    const purchaseData = {
      vendorName: vendorName,
      materialCategoryId: materialCategoryId,
      materialCategoryName: selectedCategoryObject
        ? selectedCategoryObject.categoryName
        : "",
      materialTypeId: materialTypeId,
      brandName: brandName,
      unitId: unitId,
      quantity: quantity,
      purchaseAmount: purchaseAmount,
      purchaseDate: purchaseDate
    };

    try {
      const response = await axios.post(
        "http://localhost:8089/addPurchaseDetail",
        purchaseData
      );

      console.log(
        "Purchase saved successfully:",
        response.data
      );

      if (response.data && response.data.purchase) {
        onSuccess({
          ...response.data.purchase,
          materialCategoryName: selectedCategoryObject
            ? selectedCategoryObject.categoryName
            : ""
        });
      } else {
        setErrorMessage(
          "Purchase was saved, but the saved purchase details could not be displayed."
        );
      }
    } catch (error) {
      console.error(
        "Error saving purchase:",
        error
      );

      if (error.response && error.response.data) {
        const backendError = error.response.data;

        if (
          backendError.fieldErrors &&
          Object.keys(backendError.fieldErrors).length > 0
        ) {
          const messages = Object.values(
            backendError.fieldErrors
          );

          setErrorMessage(messages.join(" "));
        } else if (
          backendError.errors &&
          backendError.errors.length > 0
        ) {
          setErrorMessage(
            backendError.errors.join(" ")
          );
        } else if (backendError.message) {
          setErrorMessage(
            backendError.message
          );
        } else {
          setErrorMessage(
            "Please correct the entered details."
          );
        }
      } else {
        setErrorMessage(
          "Unable to connect to the Inventory Management System."
        );
      }
    }
  };

  const handleReset = () => {
    setErrorMessage("");
    setSelectedCategory("");
    setTypes([]);
    setUnits([]);
  };

  return (
    <main className="page-container">
      <div className="page-heading">
        <div>
          <h2>New Purchase</h2>
          <p>
            Enter the details of the materials purchased.
          </p>
        </div>
      </div>

      {errorMessage && (
        <div className="error-message">
          {errorMessage}
        </div>
      )}

      <form
        className="purchase-card"
        onSubmit={handleSubmit}
        onReset={handleReset}
      >
        <div className="form-grid">
          <div className="form-group">
            <label>Vendor Name</label>

            <select
              name="vendorName"
              required  >
              <option value="">
                Select Vendor
              </option>

              {vendors.map((vendor) => (
                <option
                  key={vendor.vendorId}
                  value={vendor.vendorName} >
                  {vendor.vendorName}
                </option>
              ))}
            </select>
          </div>

          <div className="form-group">
            <label>Material Category</label>

            <select
              name="materialCategoryId"
              value={selectedCategory}
              onChange={handleCategoryChange}
              required >
              <option value="">
                Select Category
              </option>

              {categories.map((category) => (
                <option
                  key={category.categoryId}
                  value={category.categoryId} >
                  {category.categoryName}
                </option>
              ))}
            </select>
          </div>

          <div className="form-group">
            <label>Material Type</label>

            <select
              name="materialTypeId"
              required
            >
              <option value="">
                Select Type
              </option>

              {types.map((type) => (
                <option
                  key={type.typeId}
                  value={type.typeId} >
                  {type.typeName}
                </option>
              ))}
            </select>
          </div>

          <div className="form-group">
            <label>Unit</label>

            <select
              name="unitId"
              required >
              <option value="">
                Select Unit
              </option>

              {units.map((unit) => (
                <option
                  key={unit.unitId}
                  value={unit.unitId} >
                  {unit.unitName}
                </option>
              ))}
            </select>
          </div>

          <div className="form-group">
            <label>Brand Name</label>

            <input
              type="text"
              name="brandName"
              placeholder="Enter brand name"
              required />
          </div>

          <div className="form-group">
            <label>Quantity</label>

            <input
              type="number"
              name="quantity"
              placeholder="Enter quantity"
              min="1"
              required />
          </div>

          <div className="form-group">
            <label>Purchase Amount</label>

            <input
              type="number"
              name="purchaseAmount"
              placeholder="Enter purchase amount"
              min="0"
              step="0.01"
              required />
          </div>

          <div className="form-group">
            <label>Purchase Date</label>

            <input
              type="date"
              name="purchaseDate"
              required  />
          </div>
        </div>

        <div className="form-actions">
          <button
            type="reset"
            className="secondary-button" >
            Clear
          </button>

          <button
            type="submit"
            className="primary-button" >
            Add Purchase
          </button>
        </div>
      </form>
    </main>
  );
}

export default PurchaseEntry;