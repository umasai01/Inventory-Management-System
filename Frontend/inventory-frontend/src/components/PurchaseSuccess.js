function PurchaseSuccess({ purchaseData, onBack }) {
  const formatDate = (dateValue) => {
    if (!dateValue) {
      return "-";
    }

    return dateValue.substring(0, 10);
  };

  return (
    <main className="page-container">
      <div className="purchase-success">
        <h2>Purchase Successful!</h2>

        <p className="success-message">
          Purchase details have been added successfully.
        </p>

        <div className="success-details">
          <p>
            <strong>Purchase Id:</strong>{" "}
            {purchaseData.purchaseId}
          </p>

          <p>
            <strong>Transaction Id:</strong>{" "}
            {purchaseData.transactionId}
          </p>

          <p>
            <strong>Vendor:</strong>{" "}
            {purchaseData.vendorName}
          </p>

          <p>
            <strong>Material Category:</strong>{" "}
            {purchaseData.materialCategoryName || "-"}
          </p>

          <p>
            <strong>Brand:</strong>{" "}
            {purchaseData.brandName}
          </p>

          <p>
            <strong>Quantity:</strong>{" "}
            {purchaseData.quantity}
          </p>

          <p>
            <strong>Purchase Amount:</strong>{" "}
            {purchaseData.purchaseAmount}
          </p>

          <p>
            <strong>Purchase Date:</strong>{" "}
            {formatDate(purchaseData.purchaseDate)}
          </p>
        </div>

        <div className="success-actions">
          <button
            className="primary-button"
            onClick={onBack}
          >
            Add Another Purchase
          </button>
        </div>
      </div>
    </main>
  );
}

export default PurchaseSuccess;