package com.vikas.rating.service.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "ratings")
public class Rating {
    
    @Id
    private String ratingId;
    
    private String userId;
    
    private String hotelId;
    
    private String rating;
    
    private String feedback;
}
