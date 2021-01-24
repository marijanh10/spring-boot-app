package hranj.marijan.springbootapp.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
public class Image {

    @Column(nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, length = 200)
    @Basic
    private String path;

    @JoinColumn(name = "fk_user", referencedColumnName = "id", nullable = false)
    @ManyToOne
    private User user;

    @ManyToMany(mappedBy = "imagesSharedWithMe")
    private List<User> usersWithPermissionToSeeImage;

    public Image() {}

    public Image(String path, User user) {
        this.path = path;
        this.user = user;
    }

    public void removeUsersFromPermissions(User user) {
        usersWithPermissionToSeeImage.remove(user);
    }

}
