package com.estudo.api.commons;

import java.time.LocalDateTime;

public interface EntityBase {
    String getId();
    LocalDateTime getDateCreate();
    String getSessaoCreate();
    LocalDateTime getDateUpdate();
    String getSessaoUpdate();
}