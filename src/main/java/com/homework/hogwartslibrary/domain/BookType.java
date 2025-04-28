package com.homework.hogwartslibrary.domain;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BookType {
    NEW_RELEASE ("New Releases"),
    REGULAR ("Regular"),
    OLD_EDITION ("Old Editions");

    private final String value;
}
