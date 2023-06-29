package fetchDepthBug.domain;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Form {

    @OneToMany(mappedBy = "id.form")
    private List<FormVersion> versions;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<FormVersion> getVersions() {
        return versions;
    }

    public void setVersions(List<FormVersion> versions) {
        this.versions = versions;
    }
}
