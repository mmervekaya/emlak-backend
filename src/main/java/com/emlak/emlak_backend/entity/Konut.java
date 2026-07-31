package com.emlak.emlak_backend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

/**
 * Konut (daire/ev) ilanı. {@link Ilan} ortak alanlarını devralır,
 * konuta özel alanları ekler.
 */
@Entity
@Table(name = "konutlar")
@Getter
@Setter
public class Konut extends Ilan {

    /** "Kiralık" / "Satılık". */
    private String ilanTipi;

    /** Örn: "1+1", "2+1", "3+1". */
    private String odaSayisi;

    /** Bina yaşı (yıl). */
    private Integer binaYasi;

    /** Bulunduğu kat. "Zemin" gibi metinler için String. */
    private String bulunduguKat;

    /** Isıtma tipi (örn: Kombi, Merkezi, Yerden ısıtma). */
    private String isitmaTipi;
}
