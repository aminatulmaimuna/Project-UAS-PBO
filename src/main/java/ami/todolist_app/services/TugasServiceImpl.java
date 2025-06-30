package ami.todolist_app.services;

import ami.todolist_app.model.Tugas;
import ami.todolist_app.repository.TugasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

// Anotasi @Service ditempatkan di kelas implementasi
@Service
public class TugasServiceImpl implements TugasService {

    @Autowired
    private TugasRepository tugasRepository;

    @Override
    public List<Tugas> getAllTugas() {
        return tugasRepository.findAll();
    }

    @Override
    public Tugas saveTugas(Tugas tugas) {
        // Validasi bisa ditambahkan di sini jika perlu
        return tugasRepository.save(tugas);
    }

    // INI BAGIAN YANG MEMPERBAIKI ERROR ANDA
    @Override
    public Tugas updateTugas(Long id, Tugas tugasUpdate) {
        // 1. Cari tugas yang ada di database berdasarkan ID
        Tugas tugasLama = tugasRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID Tugas tidak ditemukan: " + id));

        // 2. Perbarui field-fieldnya dengan data baru dari request body
        tugasLama.setDeskripsi(tugasUpdate.getDeskripsi());
        tugasLama.setSelesai(tugasUpdate.isSelesai());

        // 3. Simpan perubahan ke database dan kembalikan hasilnya
        return tugasRepository.save(tugasLama);
    }

    @Override
    public void deleteTugas(Long id) {
        // Periksa dulu apakah tugasnya ada sebelum menghapus
        if (!tugasRepository.existsById(id)) {
            throw new IllegalArgumentException("ID Tugas tidak ditemukan untuk dihapus: " + id);
        }
        tugasRepository.deleteById(id);
    }
}