package com.epam.webproject.entity;

public enum RoomType {
    BALCONY_ROOM,
    DUPLEX,
    STANDARD,
    STUDIO,
    SUITE,
    MINI_SUITE;

    RoomType(String name) {
        this.name = name;
    }

    private String name;

    public String getName() {
        return this.name;
    }

    public static RoomType findByName(String name) throws EnumConstantNotPresentException {
        for (RoomType roomType : RoomType.values()) {
            if (roomType.getName().equals(name)) {
                return roomType;
            }
        }
        throw new EnumConstantNotPresentException(RoomType.class, "Error with searching room type");
    }
}
