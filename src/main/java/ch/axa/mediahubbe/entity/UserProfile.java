package ch.axa.mediahubbe.entity;

import jakarta.persistence.*;


@Entity
@Table(name = "user_profile")
public class UserProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String address;
    private String profileImgUrl;
}
