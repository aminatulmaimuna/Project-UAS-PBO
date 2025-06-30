document.addEventListener('DOMContentLoaded', () => {

    const API_URL = 'http://localhost:8080/api/tugas';

    // Seleksi elemen DOM
    const form = document.getElementById('add-task-form');
    const input = document.getElementById('new-task-input');
    const taskList = document.getElementById('task-list');
    const loadingIndicator = document.getElementById('loading-indicator');
    const dateDisplay = document.getElementById('date-display');
    const filterBtns = document.querySelectorAll('.filter-btn');

    // Seleksi elemen Modal
    const editModal = document.getElementById('edit-modal');
    const editForm = document.getElementById('edit-task-form');
    const editInput = document.getElementById('edit-task-input');
    const editIdInput = document.getElementById('edit-task-id');
    const cancelEditBtn = document.getElementById('cancel-edit-btn');

    let allTasks = [];
    let currentFilter = 'all';

    // ... (Sisa kode JavaScript dari sebelumnya) ...
    dateDisplay.textContent = new Date().toLocaleDateString('id-ID', { weekday: 'long', year: 'numeric', month: 'long', day: 'numeric' });
    const showLoading = (isLoading) => { loadingIndicator.style.display = isLoading ? 'flex' : 'none'; taskList.style.display = isLoading ? 'none' : 'block'; };
    const renderTasks = () => { let filteredTasks = allTasks; if (currentFilter === 'active') { filteredTasks = allTasks.filter(task => !task.selesai); } else if (currentFilter === 'completed') { filteredTasks = allTasks.filter(task => task.selesai); } taskList.innerHTML = ''; if (filteredTasks.length === 0) { taskList.innerHTML = `<li class="empty-state">🎉 Tugas tidak ditemukan.</li>`; return; } filteredTasks.forEach(task => { const taskElement = createTaskElement(task); taskList.appendChild(taskElement); }); };
    const createTaskElement = (task) => { const li = document.createElement('li'); li.className = `task-item ${task.selesai ? 'completed' : ''}`; li.dataset.id = task.id; const descriptionSpan = document.createElement('span'); descriptionSpan.className = 'description'; descriptionSpan.textContent = task.deskripsi; const actionsDiv = document.createElement('div'); actionsDiv.className = 'actions'; const toggleButton = createActionButton('btn-toggle', `fa ${task.selesai ? 'fa-undo-alt' : 'fa-check'}`, () => toggleTaskStatus(task.id)); const editButton = createActionButton('btn-edit', 'fa fa-pencil-alt', () => openEditModal(task)); const deleteButton = createActionButton('btn-delete', 'fa fa-trash', () => deleteTask(task.id, li)); actionsDiv.append(toggleButton, editButton, deleteButton); li.append(descriptionSpan, actionsDiv); return li; };
    const createActionButton = (className, iconClass, onClick) => { const button = document.createElement('button'); button.className = className; button.innerHTML = `<i class="${iconClass}"></i>`; button.addEventListener('click', onClick); return button; };
    const fetchTasks = async () => { showLoading(true); try { const response = await fetch(API_URL); if (!response.ok) throw new Error('Gagal mengambil data tugas.'); allTasks = await response.json(); renderTasks(); } catch (error) { taskList.innerHTML = `<li class="empty-state" style="color: var(--danger-color);">${error.message}</li>`; } finally { showLoading(false); } };
    const addTask = async (description) => { const newTask = { deskripsi: description, selesai: false }; try { const response = await fetch(API_URL, { method: 'POST', headers: { 'Content-Type': 'application/json' }, body: JSON.stringify(newTask) }); if (!response.ok) throw new Error('Gagal menambah tugas.'); await fetchTasks(); } catch (error) { alert(error.message); } };
    const toggleTaskStatus = async (id) => { const task = allTasks.find(t => t.id === id); if (!task) return; const updatedTask = { ...task, selesai: !task.selesai }; try { await fetch(`${API_URL}/${id}`, { method: 'PUT', headers: { 'Content-Type': 'application/json' }, body: JSON.stringify(updatedTask) }); await fetchTasks(); } catch (error) { alert('Gagal mengupdate status tugas.'); } };
    const deleteTask = async (id, element) => { if (!confirm('Apakah Anda yakin ingin menghapus tugas ini?')) return; element.classList.add('removing'); element.addEventListener('animationend', async () => { try { const response = await fetch(`${API_URL}/${id}`, { method: 'DELETE' }); if (!response.ok) throw new Error('Gagal menghapus tugas di server.'); await fetchTasks(); } catch (error) { alert(error.message); element.classList.remove('removing'); } }); };
    const openEditModal = (task) => { editIdInput.value = task.id; editInput.value = task.deskripsi; editModal.classList.add('show'); editInput.focus(); };
    const closeEditModal = () => { editModal.classList.remove('show'); };
    const handleUpdateTask = async (e) => { e.preventDefault(); const id = editIdInput.value; const newDeskripsi = editInput.value.trim(); const task = allTasks.find(t => t.id == id); if (!task || !newDeskripsi) return; const updatedTask = { ...task, deskripsi: newDeskripsi }; try { await fetch(`${API_URL}/${id}`, { method: 'PUT', headers: { 'Content-Type': 'application/json' }, body: JSON.stringify(updatedTask) }); closeEditModal(); await fetchTasks(); } catch (error) { alert('Gagal menyimpan perubahan.'); } };
    form.addEventListener('submit', e => { e.preventDefault(); const taskDescription = input.value.trim(); if (taskDescription) { addTask(taskDescription); input.value = ''; } });
    filterBtns.forEach(btn => { btn.addEventListener('click', () => { filterBtns.forEach(b => b.classList.remove('active')); btn.classList.add('active'); currentFilter = btn.dataset.filter; renderTasks(); }); });
    editForm.addEventListener('submit', handleUpdateTask);
    cancelEditBtn.addEventListener('click', closeEditModal);
    editModal.addEventListener('click', (e) => { if (e.target === editModal) closeEditModal(); });
    fetchTasks();
});