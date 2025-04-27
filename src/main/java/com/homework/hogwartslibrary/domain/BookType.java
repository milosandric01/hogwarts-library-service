package com.homework.hogwartslibrary.domain;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum BookType {
    NEW_RELEASE ("New Releases"),
    REGULAR ("Regular"),
    OLD_EDITION ("Old Editions");

    private final String value;
}
