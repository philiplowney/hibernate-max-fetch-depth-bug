package fetchDepthBug.domain;

import jakarta.persistence.*;

import java.util.Set;

@Entity
public class StepConfiguration {

    public StepConfiguration() { }

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "configuration", fetch = FetchType.EAGER)
    private Set<FormOption> formOptions;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<FormOption> getFormOptions() {
        return formOptions;
    }

    public void setFormOptions(Set<FormOption> formOptions) {
        this.formOptions = formOptions;
    }

}
