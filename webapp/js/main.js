const API_URL = 'http://localhost:8080/api';

async function fetchAPI(endpoint, options = {}) {
    try {
        const response = await fetch(`${API_URL}/${endpoint}`, options);
        if (!response.ok) throw new Error('API error');
        return await response.json();
    } catch (error) {
        console.error(`Error fetching ${endpoint}:`, error);
        return null;
    }
}

async function loadDashboardStats() {
    const patientCount = await fetchAPI('patients/count');
    const doctorCount = await fetchAPI('doctors/count');
    const pendingBills = await fetchAPI('bills/pending/count');
    const activeAdmissions = await fetchAPI('admissions/active/count');
    
    if (patientCount) document.getElementById('totalPatients').innerText = patientCount.count;
    if (doctorCount) document.getElementById('totalDoctors').innerText = doctorCount.count;
    if (pendingBills) document.getElementById('pendingBills').innerText = pendingBills.count;
    if (activeAdmissions) document.getElementById('activeAdmissions').innerText = activeAdmissions.count;
}

function showMessage(msg, isError = false) {
    const msgDiv = document.getElementById('message');
    if (msgDiv) {
        msgDiv.innerHTML = `<div style="background: ${isError ? '#f8d7da' : '#d4edda'}; color: ${isError ? '#721c24' : '#155724'}; padding: 10px; margin: 10px 0; border-radius: 5px;">${msg}</div>`;
        setTimeout(() => msgDiv.innerHTML = '', 3000);
    } else {
        alert(msg);
    }
}