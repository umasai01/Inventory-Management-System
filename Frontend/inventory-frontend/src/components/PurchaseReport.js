import { useEffect, useState } from "react";
import axios from "axios";

function PurchaseReport() {
  const [vendors, setVendors] = useState([]);
  const [selectedVendor, setSelectedVendor] = useState("");
  const [fromDate, setFromDate] = useState("");
  const [toDate, setToDate] = useState("");
  const [reportList, setReportList] = useState([]);
  const [selectedVendorDetails, setSelectedVendorDetails] = useState(null);
  const [errorMessage, setErrorMessage] = useState("");
  const [categoryMap, setCategoryMap] = useState({});
  const [typeMap, setTypeMap] = useState({});
  const [unitMap, setUnitMap] = useState({});
  const [searchClicked, setSearchClicked] = useState(false);

  useEffect(() => {
    axios
      .get("http://localhost:8089/vendors")
      .then((response) => {
        setVendors(response.data || []);
      })
      .catch(() => {
        setErrorMessage("Unable to load vendor list.");
      });

    axios
      .get("http://localhost:8089/categories")
      .then((response) => {
        const categories = response.data || {};
        const map = {};

        categories.forEach((category) => {
          map[category.categoryId] = category.categoryName;
        });

        setCategoryMap(map);
      })
      .catch(() => {
        setErrorMessage("Unable to load material categories.");
      });
  }, []);

  const handleVendorChange = (event) => {
    const vendorName = event.target.value;

    setSelectedVendor(vendorName);
    setReportList([]);
    setErrorMessage("");
    setSearchClicked(false);

    const vendor = vendors.find(
      (item) => item.vendorName === vendorName
    );

    setSelectedVendorDetails(vendor || null);
  };

  const handleSearch = async (event) => {
    event.preventDefault();

    setSearchClicked(true);
    setErrorMessage("");
    setReportList([]);

    if (!selectedVendor) {
      setErrorMessage("Please select a vendor.");
      return;
    }

    if (!fromDate || !toDate) {
      setErrorMessage("Please select From Date and To Date.");
      return;
    }

    if (fromDate > toDate) {
      setErrorMessage("From Date cannot be after To Date.");
      return;
    }

    try {
      const response = await axios.post(
        "http://localhost:8089/report/controller/getPurchaseDetails",
        {
          fromDate: fromDate,
          toDate: toDate,
          vendorName: selectedVendor
        }
      );

      const data = response.data || [];

      const newTypeMap = {};
      const newUnitMap = {};

      const categoryIds = [
        ...new Set(
          data
            .map((purchase) => purchase.materialCategoryId)
            .filter(Boolean)
        )
      ];

      for (const categoryId of categoryIds) {
        try {
          const typeUnitResponse = await axios.post(
            "http://localhost:8089/getUnitAndTypeList",
            {
              materialCategoryId: categoryId
            }
          );

          const unitList =
            typeUnitResponse.data?.unitList || [];

          const typeList =
            typeUnitResponse.data?.materialTypeList || [];

          typeList.forEach((type) => {
            newTypeMap[type.typeId] = type.typeName;
          });

          unitList.forEach((unit) => {
            newUnitMap[unit.unitId] = unit.unitName;
          });
        } catch (error) {
          console.log(
            "Unable to load material type/unit details."
          );
        }
      }

      setTypeMap(newTypeMap);
      setUnitMap(newUnitMap);

      const sortedData = [...data].sort(
        (first, second) =>
          Number(first.purchaseId) -
          Number(second.purchaseId)
      );

      setReportList(sortedData);
    } catch (error) {
      setErrorMessage("Unable to fetch purchase report.");
    }
  };

  return (
    <main className="page-container">

      <div className="page-heading">
        <h2>Vendor Purchase Report</h2>

        <p>
          View purchase details based on vendor and date range.
        </p>
      </div>

      <div className="report-filter-card">

        <form onSubmit={handleSearch}>

          <div className="report-filter-grid">

            <div className="form-group">
              <label>Vendor Name</label>

              <select
                value={selectedVendor}
                onChange={handleVendorChange}
              >
                <option value="">
                  Select Vendor
                </option>

                {vendors.map((vendor) => (
                  <option
                    key={vendor.vendorId}
                    value={vendor.vendorName}
                  >
                    {vendor.vendorName}
                  </option>
                ))}
              </select>
            </div>

            <div className="form-group">
              <label>From Date</label>

              <input
                type="date"
                value={fromDate}
                onChange={(event) =>
                  setFromDate(event.target.value)
                }
              />
            </div>

            <div className="form-group">
              <label>To Date</label>

              <input
                type="date"
                value={toDate}
                onChange={(event) =>
                  setToDate(event.target.value)
                }
              />
            </div>

            <div className="form-group search-group">
              <label>&nbsp;</label>

              <button
                type="submit"
                className="search-button"
              >
                Search Report
              </button>
            </div>

          </div>

          {selectedVendorDetails && (
            <div className="vendor-information">

              <div className="vendor-info-item">
                <span className="vendor-info-label">
                  Address
                </span>

                <span className="vendor-info-value">
                  {selectedVendorDetails.vendorAddress || "-"}
                </span>
              </div>

              <div className="vendor-info-item">
                <span className="vendor-info-label">
                  Contact Number
                </span>

                <span className="vendor-info-value">
                  {selectedVendorDetails.contactNumber || "-"}
                </span>
              </div>

              <div className="vendor-info-item">
                <span className="vendor-info-label">
                  Contact Person
                </span>

                <span className="vendor-info-value">
                  {selectedVendorDetails.contactPerson || "-"}
                </span>
              </div>

            </div>
          )}

        </form>

      </div>

      {errorMessage && (
        <div className="report-error">
          {errorMessage}
        </div>
      )}

      {reportList.length > 0 && (
        <div className="report-results">

          <div className="report-results-header">

            <h3>Purchase Details</h3>

            <span>
              {reportList.length} record
              {reportList.length > 1 ? "s" : ""} found
            </span>

          </div>

          <div className="table-container">

            <table>

              <thead>
                <tr>
                  <th>Material Category</th>
                  <th>Material Type</th>
                  <th>Brand</th>
                  <th>Quantity</th>
                  <th>Unit</th>
                  <th>Amount</th>
                  <th>Purchase Date</th>
                </tr>
              </thead>

              <tbody>
                {reportList.map((purchase, index) => (
                  <tr
                    key={purchase.purchaseId || index}
                  >
                    <td>
                      {categoryMap[purchase.materialCategoryId] ||
                        purchase.materialCategoryName ||
                        "-"}
                    </td>

                    <td>
                      {typeMap[purchase.materialTypeId] ||
                        purchase.materialTypeName ||
                        "-"}
                    </td>

                    <td>
                      {purchase.brandName || "-"}
                    </td>

                    <td>
                      {purchase.quantity ?? "-"}
                    </td>

                    <td>
                      {unitMap[purchase.unitId] ||
                        purchase.materialUnitName ||
                        "-"}
                    </td>

                    <td>
                      {purchase.purchaseAmount ?? "-"}
                    </td>

                    <td>
                      {purchase.purchaseDate || "-"}
                    </td>
                  </tr>
                ))}
              </tbody>

            </table>

          </div>

        </div>
      )}

      {searchClicked &&
        reportList.length === 0 &&
        selectedVendor &&
        fromDate &&
        toDate &&
        !errorMessage && (
          <div className="no-records">
            No records found.
          </div>
        )}

    </main>
  );
}

export default PurchaseReport;