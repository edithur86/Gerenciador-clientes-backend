package com.estudo.api.commons;

import java.time.LocalDateTime;

public interface EntityBaseInteger {
    Integer getId();

    LocalDateTime getDateCreate();

    String getSessaoCreate();

    LocalDateTime getDateUpdate();

    String getSessaoUpdate();
}