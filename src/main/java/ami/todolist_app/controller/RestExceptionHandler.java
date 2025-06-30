package ami.todolist_app.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * Handler ini akan menangkap semua error dari TugasRestController
 * dan mengubahnya menjadi pesan JSON yang informatif.
 */
// PENTING: Pastikan kelas TugasRestController di-import dengan benar
// dan assignableTypes menunjuk ke controller yang benar.
@ControllerAdvice(assignableTypes = TugasRestController.class)
public class RestExceptionHandler {

    /**
     * Menangani error ketika ID tidak ditemukan.
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException ex) {
        
        Map<String, Object> body = Map.of(
                "timestamp", LocalDateTime.now(),
                "status", HttpStatus.NOT_FOUND.value(),
                "error", "Not Found",
                "message", ex.getMessage()
        );

        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }
    
    /**
     * Menangani error umum lainnya sebagai Bad Request untuk mencegah 500.
     * Ini akan menangkap NullPointerException, dll.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGenericException(Exception ex) {
        
        Map<String, Object> body = Map.of(
                "timestamp", LocalDateTime.now(),
                "status", HttpStatus.BAD_REQUEST.value(),
                "error", "Bad Request",
                "message", "Terjadi kesalahan pada server: " + ex.getMessage()
        );

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }
}