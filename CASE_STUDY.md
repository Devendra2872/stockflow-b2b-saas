 CASE STUDY – StockFlow (B2B Inventory Management System)

Author: Devendra Patil
Tech Stack: Java, Spring Boot, Spring Data JPA, MySQL
Time Allocation: 90 minutes (Take-home)

Part 1: Code Review & Debugging
 Issues Identified

No SKU Uniqueness Check
SKUs must be unique across the platform. Missing validation can lead to duplicate products and data corruption.

Partial Database Commits
Multiple commits (db.session.commit() called twice) can cause inconsistent state if one operation fails.

No Error Handling
If product or inventory creation fails, the API throws a raw error instead of returning a structured message.

No Transaction Management
Product and inventory should be created atomically to prevent orphaned records.

Product Limited to One Warehouse
Business requirement says products can exist in multiple warehouses — the code violates this.

Missing Field Validation
No checks for required or optional fields; malformed data may crash the API.

No Decimal Validation for Price
Prices should allow decimal values but were not validated or normalized.

No Response Standardization
Error and success responses aren’t consistent or informative.

Fixed Logic Summary

Added transaction management to ensure atomic writes.

Enforced unique SKU validation before product creation.

Included error handling and proper HTTP responses.

Allowed product-to-multiple-warehouse relationships.

Validated all fields before DB insertion.

Ensured price supports decimal values.

Used consistent and descriptive response messages.



Part 2: Database Design
Proposed Schema (Conceptual)

Companies – Each company using StockFlow.

Warehouses – Belong to a company, can hold many products.

Products – Each product identified by a unique SKU.

Inventory – Tracks product quantity in each warehouse.

Suppliers – Provide products to companies.

ProductSuppliers – Many-to-many relation between products and suppliers.

ProductThresholds – Defines low-stock levels per product.

InventoryAudit – Logs every stock level change (increases/decreases).


Key Design Decisions

Normalization: Reduced data duplication and ensured scalability.

Referential Integrity: Enforced via foreign keys between related tables.

Audit Trail: Added InventoryAudit for tracking changes over time.

Indexes: Applied to sku, warehouse_id, and product_id for quick lookups.

Many-to-Many Handling: Used linking tables for flexible supplier-product mapping.

Threshold Configuration: Made configurable per product to support custom alerts.




Questions for Product Team

Should low-stock thresholds vary by warehouse or remain global per product?

How should bundle products be represented — nested or separate SKUs?

What defines “recent sales activity” (e.g., last 7, 14, or 30 days)?

Should suppliers be required for all products?

Should inventory changes track the user who performed the action?

How frequently should alerts be recalculated — real-time or scheduled?




Design Rationale :-

The schema is optimized for read-heavy operations (like reporting and alerting).

Separating inventory and product enables multi-warehouse flexibility.

Auditing and thresholds ensure traceability and business rule compliance.

Schema supports future scalability (multi-company, multi-supplier environment).



Part 3: API Implementation – Low Stock Alerts
 Business Rules

Low-stock threshold varies by product type.

Only alert for products with recent sales activity.

Must support multiple warehouses per company.

Include supplier information for reordering.

Must gracefully handle missing data (no supplier, unknown threshold).



Expected Behavior :-

For each company, gather all products stored in its warehouses.

Compare current stock vs. defined threshold.

Include only products that have had recent sales or stock changes.

Attach supplier details for easy reordering.

Return a list of all low-stock items with total count.





Response Structure :-

Each alert entry should include:

Product details (ID, name, SKU)

Warehouse details (ID, name)

Current stock and threshold

Estimated days until stockout (based on sales velocity)

Supplier contact info




Edge Cases Considered :-

Missing or undefined threshold → use a default (e.g., 10 units).

No suppliers found → omit supplier section gracefully.

Zero stock but inactive product → ignore alert.

Multiple warehouses → treat inventory per warehouse separately.

Transaction or DB errors → handled gracefully with meaningful messages.




Approach Summary :-

Data Aggregation: Join product, inventory, and threshold data for each warehouse.

Filtering Logic: Apply stock < threshold and recent sales filter.

Response Composition: Build structured JSON response with supplier info.

Performance Considerations:

Index on (company_id, product_id) for faster alert generation.

Preload thresholds and suppliers to avoid N+1 queries.

Scalability: Can later move alert computation to a background job or cron task.

Assumptions

“Recent sales activity” means sales within the last 30 days.

Low-stock threshold stored per product, not per warehouse.

Supplier info is optional, but included if available.

Product and inventory creation handled in one atomic transaction.

API responses are JSON-formatted with consistent status codes.

Real-time stock updates supported through transactional updates or WebSocket feeds.



Summary

Category	         Key Points
Code Review	         Fixed data validation, atomicity, and business rule issues
Database             Design	Normalized schema with scalable relationships
API Design	         Clear rules for low-stock detection and supplier inclusion
Edge Handling	     Robust handling for missing or partial data
Scalability	         Supports multi-company, multi-warehouse setup
Maintainability	     Clear separation between product, inventory, and audit layers