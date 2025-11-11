// Dashboard functionality
document.addEventListener('DOMContentLoaded', function() {
    // Initialize sorting
    initializeSorting();
    
    // Initialize filtering
    initializeFiltering();
});

function initializeSorting() {
    const sortableHeaders = document.querySelectorAll('th.sortable');
    sortableHeaders.forEach(header => {
        header.addEventListener('click', () => {
            const table = header.closest('table');
            const tbody = table.querySelector('tbody');
            const rows = Array.from(tbody.querySelectorAll('tr'));
            const columnIndex = Array.from(header.parentNode.children).indexOf(header);
            const isAsc = header.classList.contains('asc');
            
            // Update sort indicators
            sortableHeaders.forEach(h => {
                h.classList.remove('asc', 'desc');
                h.querySelector('.sort-indicator')?.remove();
            });
            
            // Add new sort indicator
            header.classList.add(isAsc ? 'desc' : 'asc');
            const indicator = document.createElement('span');
            indicator.className = `sort-indicator ${isAsc ? 'desc' : 'asc'}`;
            header.appendChild(indicator);
            
            // Sort rows
            rows.sort((a, b) => {
                const aValue = a.children[columnIndex].textContent;
                const bValue = b.children[columnIndex].textContent;
                return isAsc ? 
                    bValue.localeCompare(aValue, undefined, {numeric: true}) :
                    aValue.localeCompare(bValue, undefined, {numeric: true});
            });
            
            // Update table
            rows.forEach(row => tbody.appendChild(row));
        });
    });
}

function initializeFiltering() {
    const filterInput = document.getElementById('payment-filter');
    if (!filterInput) return;
    
    filterInput.addEventListener('input', (e) => {
        const searchTerm = e.target.value.toLowerCase();
        const rows = document.querySelectorAll('tbody tr');
        
        rows.forEach(row => {
            const text = row.textContent.toLowerCase();
            row.style.display = text.includes(searchTerm) ? '' : 'none';
        });
    });
}

// Format currency amounts
function formatCurrency(amount, currency = 'USD') {
    return new Intl.NumberFormat('en-US', {
        style: 'currency',
        currency: currency
    }).format(amount);
}

// Update validation messages
function updateValidationMessages(validationResult, container) {
    container.innerHTML = '';
    
    if (!validationResult.valid) {
        const message = document.createElement('div');
        message.className = `validation-message ${validationResult.errorType.toLowerCase()}`;
        message.textContent = validationResult.message;
        container.appendChild(message);
    }
}

// Calculate and display fees
function updateFeeCalculation(payment, container) {
    const feeCalc = payment.feeCalculation;
    container.innerHTML = `
        <div class="fee-calculation">
            <div>Original Amount: <span class="amount">${formatCurrency(feeCalc.originalAmount)}</span></div>
            <div>Fee (${feeCalc.feePercentage}%): <span class="amount">${formatCurrency(feeCalc.feeAmount)}</span></div>
            <div>Total Amount: <span class="amount">${formatCurrency(feeCalc.totalAmount)}</span></div>
        </div>
    `;
}
