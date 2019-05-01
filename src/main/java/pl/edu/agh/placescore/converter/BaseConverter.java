package pl.edu.agh.placescore.converter;

public interface BaseConverter<T, U> {
    T fromDTO(U dto);

    U fromEntity(T entity);
}
