package ami.todolist_app.controller;

import ami.todolist_app.model.Tugas;
// Pastikan yang di-import adalah INTERFACE, bukan kelas implementasi
import ami.todolist_app.services.TugasService; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tugas")
public class TugasRestController {

    @Autowired
    private TugasService tugasService; // Spring akan otomatis menyuntikkan TugasServiceImpl

    // GET: Mendapatkan semua tugas
    @GetMapping
    public List<Tugas> getAllTugas() {
        return tugasService.getAllTugas();
    }

    // POST: Membuat tugas baru
    @PostMapping
    public Tugas createTugas(@RequestBody Tugas tugas) {
        // PENYESUAIAN: Sekarang kita mengembalikan hasil dari service,
        // yang sudah berisi ID dari database.
        return tugasService.saveTugas(tugas);
    }

    // PUT: Memperbarui tugas secara keseluruhan
    @PutMapping("/{id}")
    public Tugas updateTugas(@PathVariable Long id, @RequestBody Tugas tugasUpdate) {
        return tugasService.updateTugas(id, tugasUpdate);
    }

    // DELETE: Menghapus tugas
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTugas(@PathVariable Long id) {
        tugasService.deleteTugas(id);
        return ResponseEntity.ok().build();
    }
}
