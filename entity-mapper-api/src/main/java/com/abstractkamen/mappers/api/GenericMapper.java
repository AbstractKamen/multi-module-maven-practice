package com.abstractkamen.mappers.api;

public interface GenericMapper<Entity, Dto> {

    Entity fromDto(Dto dto);

    Dto toDto(Entity target);
}
