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

import com.emlak.emlak_backend.entity.Konut;
import com.emlak.emlak_backend.repository.KonutRepository;

/**
 * Konut ilanları için REST uç noktaları.
 * React ön yüzünden gelen istekleri karşılar.
 */
@RestController
@RequestMapping("/api/konutlar")
@CrossOrigin(origins = "*")
public class KonutController {

    private final KonutRepository konutRepository;

    public KonutController(KonutRepository konutRepository) {
        this.konutRepository = konutRepository;
    }

    /** Tüm konutları listeler. */
    @GetMapping
    public List<Konut> tumunuGetir() {
        return konutRepository.findAll();
    }

    /** Tek bir konutu id ile getirir. */
    @GetMapping("/{id}")
    public ResponseEntity<Konut> getir(@PathVariable Long id) {
        return konutRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /** Yeni konut ekler. */
    @PostMapping
    public ResponseEntity<Konut> ekle(@RequestBody Konut konut) {
        konut.setId(null); // Yeni kayıt: id'yi veritabanı üretsin.
        Konut kaydedilen = konutRepository.save(konut);
        return ResponseEntity.status(HttpStatus.CREATED).body(kaydedilen);
    }

    /** Var olan konutu günceller. */
    @PutMapping("/{id}")
    public ResponseEntity<Konut> guncelle(@PathVariable Long id, @RequestBody Konut gelen) {
        return konutRepository.findById(id)
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
                    // Konuta özel alanlar
                    mevcut.setIlanTipi(gelen.getIlanTipi());
                    mevcut.setOdaSayisi(gelen.getOdaSayisi());
                    mevcut.setBinaYasi(gelen.getBinaYasi());
                    mevcut.setBulunduguKat(gelen.getBulunduguKat());
                    mevcut.setIsitmaTipi(gelen.getIsitmaTipi());
                    return ResponseEntity.ok(konutRepository.save(mevcut));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    /** Konut siler. */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> sil(@PathVariable Long id) {
        if (!konutRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        konutRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
