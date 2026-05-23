function showSection(sectionId) {
    document.querySelectorAll('.section').forEach(section => section.classList.remove('active'));
    document.querySelectorAll('.nav-btn').forEach(btn => btn.classList.remove('active'));
    document.getElementById(sectionId).classList.add('active');
    event.target.classList.add('active');
    if (sectionId === 'dashboard') loadDashboard();
    else if (sectionId === 'patients') loadPatients();
    else if (sectionId === 'doctors') loadDoctors();
    else if (sectionId === 'appointments') loadAppointments();
    else if (sectionId === 'bills') loadBills();
    else if (sectionId === 'rooms') loadRooms();
    else if (sectionId === 'admissions') loadAdmissions();
    else if (sectionId === 'medicines') loadMedicines();
    else if (sectionId === 'prescriptions') loadPrescriptions();
}

function loadDashboard() {
    document.getElementById('totalPatients').innerText = '5';
    document.getElementById('totalDoctors').innerText = '5';
    document.getElementById('totalAppointments').innerText = '5';
    document.getElementById('totalRooms').innerText = '7';
    document.getElementById('availableRooms').innerText = '4';
    document.getElementById('totalMedicines').innerText = '5';
    document.getElementById('pendingBills').innerText = '2';
    document.getElementById('activeAdmissions').innerText = '2';
}

function loadPatients() {
    const patients = [
        {id:1, firstName:'John', lastName:'Doe', dob:'1985-03-15', gender:'Male', phone:'555-0101', blood:'O+', email:'john@email.com', address:'123 Main St'},
        {id:2, firstName:'Jane', lastName:'Smith', dob:'1990-07-22', gender:'Female', phone:'555-0102', blood:'A+', email:'jane@email.com', address:'456 Oak Ave'},
        {id:3, firstName:'Robert', lastName:'Johnson', dob:'1978-11-30', gender:'Male', phone:'555-0103', blood:'B+', email:'robert@email.com', address:'789 Pine Rd'},
        {id:4, firstName:'Maria', lastName:'Garcia', dob:'1995-01-10', gender:'Female', phone:'555-0104', blood:'AB+', email:'maria@email.com', address:'321 Elm St'},
        {id:5, firstName:'David', lastName:'Lee', dob:'1965-09-05', gender:'Male', phone:'555-0105', blood:'O-', email:'david@email.com', address:'654 Maple Dr'}
    ];
    const tbody = document.getElementById('patientsList');
    tbody.innerHTML = '';
    patients.forEach(p => {
        tbody.innerHTML += `<tr>
            <td>${p.id}</td><td>${p.firstName} ${p.lastName}</td><td>${p.dob}</td>
            <td>${p.gender}</td><td>${p.phone}</td><td>${p.blood}</td>
            <td><button class="delete-btn" onclick="deletePatient(${p.id})">Delete</button></td>
        </tr>`;
    });
}

function loadDoctors() {
    const doctors = [
        {id:1, firstName:'Emily', lastName:'Wilson', specialization:'Cardiologist', phone:'555-0201', fee:'$150', exp:12, room:'A101'},
        {id:2, firstName:'Michael', lastName:'Brown', specialization:'Neurologist', phone:'555-0202', fee:'$200', exp:15, room:'B202'},
        {id:3, firstName:'Sarah', lastName:'Davis', specialization:'Pediatrician', phone:'555-0203', fee:'$100', exp:8, room:'C303'},
        {id:4, firstName:'James', lastName:'Miller', specialization:'Orthopedic', phone:'555-0204', fee:'$175', exp:10, room:'D404'},
        {id:5, firstName:'Lisa', lastName:'Taylor', specialization:'Dermatologist', phone:'555-0205', fee:'$125', exp:6, room:'E505'}
    ];
    const tbody = document.getElementById('doctorsList');
    tbody.innerHTML = '';
    doctors.forEach(d => {
        tbody.innerHTML += `<tr>
            <td>${d.id}</td><td>Dr. ${d.firstName} ${d.lastName}</td><td>${d.specialization}</td>
            <td>${d.phone}</td><td>${d.fee}</td><td>${d.exp} years</td><td>${d.room}</td>
        </tr>`;
    });
}

function loadAppointments() {
    const appointments = [
        {id:1, patient:'John Doe', doctor:'Dr. Emily Wilson', date:'2025-01-15', time:'10:00', status:'Completed', symptoms:'Chest pain'},
        {id:2, patient:'Jane Smith', doctor:'Dr. Sarah Davis', date:'2025-01-16', time:'14:30', status:'Scheduled', symptoms:'Fever, cough'},
        {id:3, patient:'Robert Johnson', doctor:'Dr. Michael Brown', date:'2025-01-17', time:'11:00', status:'Scheduled', symptoms:'Headache'},
        {id:4, patient:'John Doe', doctor:'Dr. James Miller', date:'2025-01-18', time:'09:30', status:'Completed', symptoms:'Knee pain'},
        {id:5, patient:'Maria Garcia', doctor:'Dr. Lisa Taylor', date:'2025-01-19', time:'15:00', status:'Scheduled', symptoms:'Skin rash'}
    ];
    const tbody = document.getElementById('appointmentsList');
    tbody.innerHTML = '';
    appointments.forEach(a => {
        tbody.innerHTML += `<tr>
            <td>${a.id}</td><td>${a.patient}</td><td>${a.doctor}</td>
            <td>${a.date}</td><td>${a.time}</td><td>${a.status}</td><td>${a.symptoms}</td>
        </tr>`;
    });
}

function loadBills() {
    const bills = [
        {id:1, patient:'John Doe', total:500, paid:500, status:'Paid', date:'2025-01-20'},
        {id:2, patient:'Jane Smith', total:300, paid:0, status:'Pending', date:null},
        {id:3, patient:'Robert Johnson', total:450, paid:200, status:'Partial', date:null}
    ];
    const tbody = document.getElementById('billsList');
    tbody.innerHTML = '';
    bills.forEach(b => {
        tbody.innerHTML += `<tr>
            <td>${b.id}</td><td>${b.patient}</td><td>$${b.total}</td>
            <td>$${b.paid}</td><td>${b.status}</td><td>${b.date || '-'}</td>
        </tr>`;
    });
}

function loadRooms() {
    const rooms = [
        {id:1, number:'101', type:'General', charge:500, available:'Yes'},
        {id:2, number:'102', type:'General', charge:500, available:'Yes'},
        {id:3, number:'201', type:'Private', charge:1200, available:'Yes'},
        {id:4, number:'202', type:'Private', charge:1200, available:'No'},
        {id:5, number:'301', type:'ICU', charge:3000, available:'Yes'},
        {id:6, number:'ICU-1', type:'ICU', charge:3500, available:'No'},
        {id:7, number:'ER-1', type:'Emergency', charge:800, available:'Yes'}
    ];
    const tbody = document.getElementById('roomsList');
    tbody.innerHTML = '';
    rooms.forEach(r => {
        tbody.innerHTML += `<tr>
            <td>${r.id}</td><td>${r.number}</td><td>${r.type}</td>
            <td>$${r.charge}</td><td>${r.available}</td>
        </tr>`;
    });
}

function loadAdmissions() {
    const admissions = [
        {id:1, patient:'John Doe', room:'202', admit:'2025-01-10 10:00', discharge:null, reason:'Heart surgery'},
        {id:2, patient:'Jane Smith', room:'ICU-1', admit:'2025-01-12 14:30', discharge:null, reason:'Severe fever'}
    ];
    const tbody = document.getElementById('admissionsList');
    tbody.innerHTML = '';
    admissions.forEach(a => {
        tbody.innerHTML += `<tr>
            <td>${a.id}</td><td>${a.patient}</td><td>${a.room}</td>
            <td>${a.admit}</td><td>${a.discharge || 'Still admitted'}</td><td>${a.reason}</td>
        </tr>`;
    });
}

function loadMedicines() {
    const medicines = [
        {id:1, name:'Paracetamol 500mg', manufacturer:'MediPharma', price:5.00, stock:500},
        {id:2, name:'Amoxicillin 250mg', manufacturer:'BioCure', price:15.00, stock:300},
        {id:3, name:'Lisinopril 10mg', manufacturer:'HeartCare', price:25.00, stock:150},
        {id:4, name:'Ibuprofen 400mg', manufacturer:'PainRelief', price:8.00, stock:400},
        {id:5, name:'Metformin 500mg', manufacturer:'DiabeCure', price:12.00, stock:200}
    ];
    const tbody = document.getElementById('medicinesList');
    tbody.innerHTML = '';
    medicines.forEach(m => {
        tbody.innerHTML += `<tr>
            <td>${m.id}</td><td>${m.name}</td><td>${m.manufacturer}</td>
            <td>$${m.price}</td><td>${m.stock}</td>
        </tr>`;
    });
}

function loadPrescriptions() {
    const prescriptions = [
        {id:1, patient:'John Doe', doctor:'Dr. Emily Wilson', medicine:'Lisinopril 10mg', dosage:'1 tablet daily', duration:30, date:'2025-01-15'},
        {id:2, patient:'Jane Smith', doctor:'Dr. Sarah Davis', medicine:'Amoxicillin 250mg', dosage:'2 tablets daily', duration:7, date:'2025-01-16'}
    ];
    const tbody = document.getElementById('prescriptionsList');
    tbody.innerHTML = '';
    prescriptions.forEach(p => {
        tbody.innerHTML += `<tr>
            <td>${p.id}</td><td>${p.patient}</td><td>${p.doctor}</td>
            <td>${p.medicine}</td><td>${p.dosage}</td><td>${p.duration}</td><td>${p.date}</td>
        </tr>`;
    });
}

function showAddPatientForm() { document.getElementById('addPatientForm').style.display = 'block'; }
function hideAddPatientForm() { document.getElementById('addPatientForm').style.display = 'none'; }
function addPatient() { alert('Patient added! (Connect to Java backend for live DB)'); hideAddPatientForm(); loadPatients(); }
function deletePatient(id) { if(confirm('Delete patient?')) { alert('Patient deleted!'); loadPatients(); } }

window.onload = () => loadDashboard();