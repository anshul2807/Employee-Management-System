package com.anshul.EmployeeManagementSystem.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "jobtitle")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobTitle {
    @Id
    private ObjectId id;
    @Indexed(unique = true)
    private String name;

    private String level;

    private String jobDescription;
}
