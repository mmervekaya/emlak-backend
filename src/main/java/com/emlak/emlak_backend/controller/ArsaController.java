package com.emlak.emlak_backend.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.emlak.emlak_backend.entity.Arsa;
import com.emlak.emlak_backend.repository.ArsaRepository;

/**
 * Arsa ilanları için REST uç noktaları.
 * React ön yüzünden gelen istekleri karşılar.
 */
@RestController
@RequestMapping("/api/arsalar")
@CrossOrigin(origins = "*")
public class ArsaController {

    private final ArsaRepository arsaRepository;

    public ArsaController(ArsaRepository arsaRepository) {
        this.arsaRepository = arsaRepository;
    }

    /** Tüm arsaları listeler. */
    @GetMapping
    public List<Arsa> tumunuGetir() {
        return arsaRepository.findAll();
    }

    /** Tek bir arsayı id ile getirir. */
    @GetMapping("/{id}")
    public ResponseEntity<Arsa> getir(@PathVariable Long id) {
        return arsaRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /** Yeni arsa ekler. */
    @PostMapping
    public ResponseEntity<Arsa> ekle(@RequestBody Arsa arsa) {
        arsa.setId(null); // Yeni kayıt: id'yi veritabanı üretsin.
        Arsa kaydedilen = arsaRepository.save(arsa);
        return ResponseEntity.status(HttpStatus.CREATED).body(kaydedilen);
    }

    /** Var olan arsayı günceller. */
    @PutMapping("/{id}")
    public ResponseEntity<Arsa> guncelle(@PathVariable Long id, @RequestBody Arsa gelen) {
        return arsaRepository.findById(id)
                .map(mevcut -> {
                    // Ortak alanlar
                    mevcut.setBaslik(gelen.getBaslik());
                    mevcut.setAciklama(gelen.getAciklama());
                    mevcut.setIlce(gelen.getIlce());
                    mevcut.setMahalle(gelen.getMahalle());
                    mevcut.setFiyat(gelen.getFiyat());
                    mevcut.setMetrekare(gelen.getMetrekare());
                    mevcut.setAcikAdres(gelen.getAcikAdres());
                    mevcut.setEvSahibiAdSoyad(gelen.getEvSahibiAdSoyad());
                    mevcut.setEvSahibiTelefon(gelen.getEvSahibiTelefon());
                    if (gelen.getDurum() != null) {
                        mevcut.setDurum(gelen.getDurum());
                    }
                    // Arsaya özel alanlar
                    mevcut.setAdaNo(gelen.getAdaNo());
                    mevcut.setPaftaNo(gelen.getPaftaNo());
                    mevcut.setImarDurumu(gelen.getImarDurumu());
                    return ResponseEntity.ok(arsaRepository.save(mevcut));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    /** Arsa siler. */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> sil(@PathVariable Long id) {
        if (!arsaRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        arsaRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
