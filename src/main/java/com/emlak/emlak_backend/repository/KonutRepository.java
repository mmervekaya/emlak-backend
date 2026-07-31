package com.emlak.emlak_backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.emlak.emlak_backend.entity.Konut;

/**
 * Konut ilanları için veri erişim katmanı.
 * JpaRepository temel CRUD operasyonlarını sağlar.
 */
@Repository
public interface KonutRepository extends JpaRepository<Konut, Long> {

    /** Belirli bir duruma (Aktif/Arşivlenmiş) göre konutları getirir. */
    List<Konut> findByDurum(String durum);
}
