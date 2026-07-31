package com.emlak.emlak_backend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

/**
 * Arsa ilanı. {@link Ilan} ortak alanlarını devralır,
 * arsaya özel alanları ekler.
 */
@Entity
@Table(name = "arsalar")
@Getter
@Setter
public class Arsa extends Ilan {

    /** Ada numarası. */
    private String adaNo;

    /** Pafta numarası. */
    private String paftaNo;

    /** İmar durumu (örn: Konut imarlı, Tarla, Ticari). */
    private String imarDurumu;
}
