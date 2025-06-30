package ami.todolist_app.services;

import ami.todolist_app.model.Tugas;
import java.util.List;

public interface TugasService {

    List<Tugas> getAllTugas();

    // Mengembalikan objek Tugas yang sudah tersimpan (lengkap dengan ID)
    Tugas saveTugas(Tugas tugas);

    // Metode yang sebelumnya error, sekarang didefinisikan di sini
    Tugas updateTugas(Long id, Tugas tugasUpdate);

    void deleteTugas(Long id);
}