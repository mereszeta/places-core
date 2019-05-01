package pl.edu.agh.placescore.converter;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import pl.edu.agh.placescore.model.dto.ReviewDTO;
import pl.edu.agh.placescore.model.entity.ReviewEntity;

@Component
@NoArgsConstructor
public class ReviewConverter implements BaseConverter<ReviewEntity, ReviewDTO> {

    @Override
    public ReviewEntity fromDTO(ReviewDTO dto) {
        return new ReviewEntity(dto.getDate(), dto.getAuthor(), dto.getRating(), dto.getText());
    }

    @Override
    public ReviewDTO fromEntity(ReviewEntity entity) {
        return new ReviewDTO(entity.getDate(), entity.getAuthor(), entity.getRating(), entity.getText(), "SELF",
            "SELF ");
    }
}
