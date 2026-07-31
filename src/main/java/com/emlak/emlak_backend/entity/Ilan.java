package com.emlak.emlak_backend.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Getter;
import lombok.Setter;

/**
 * Tüm ilan türlerinin (Konut, Arsa) paylaştığı ortak alanları barındıran
 * temel sınıf. {@link MappedSuperclass} olduğu için kendi tablosu yoktur;
 * alt sınıfların tablolarına sütun olarak yansır.
 */
@MappedSuperclass
@Getter
@Setter
public abstract class Ilan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** İlan başlığı (opsiyonel). */
    private String baslik;

    /** Serbest metin açıklama (opsiyonel). */
    @Column(length = 2000)
    private String aciklama;

    /** İlçe (örn: Gölcük). Konum bilgisi ilçe + mahalle olarak ikiye ayrıldı. */
    @Column(nullable = false)
    private String ilce;

    /** Mahalle (örn: Merkez Mah.). */
    @Column(nullable = false)
    private String mahalle;

    /** Fiyat (₺). Para hassasiyeti için BigDecimal. */
    @Column(nullable = false)
    private BigDecimal fiyat;

    /** Yüzölçümü (m²). */
    private Double metrekare;

    /** Açık adres (gizli bilgi). */
    @Column(length = 500)
    private String acikAdres;

    /** İlan sahibi ad-soyad (gizli bilgi). */
    private String evSahibiAdSoyad;

    /** İlan sahibi telefon (gizli bilgi). */
    private String evSahibiTelefon;

    /**
     * İlan durumu: "Aktif" veya "Arşivlenmiş".
     * Ön yüz ile uyumlu olması için string tutuluyor.
     */
    @Column(nullable = false)
    private String durum = "Aktif";

    private LocalDateTime olusturmaTarihi;

    private LocalDateTime guncellemeTarihi;

    @PrePersist
    protected void olusturuldugundaCalis() {
        LocalDateTime now = LocalDateTime.now();
        this.olusturmaTarihi = now;
        this.guncellemeTarihi = now;
        if (this.durum == null || this.durum.isBlank()) {
            this.durum = "Aktif";
        }
    }

    @PreUpdate
    protected void guncellendigindeCalis() {
        this.guncellemeTarihi = LocalDateTime.now();
    }
}
