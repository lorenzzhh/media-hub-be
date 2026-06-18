package ch.axa.mediahubbe.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Media {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String filename;
    private String type;
    private boolean visibility;
    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private AppUser owner;

    private String title;
    private String description;

    @Version
    private Long version;

}
