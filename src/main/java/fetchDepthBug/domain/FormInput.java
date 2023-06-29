package fetchDepthBug.domain;

import jakarta.persistence.*;

@Entity
public class FormInput {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @ManyToOne(optional = false)
    @JoinColumns(foreignKey = @ForeignKey(name = "FK_Input_FormVersion"), value = {@JoinColumn(name = "form_id", updatable = false), @JoinColumn(name = "versionNumber", updatable = false)})
    private FormVersion formVersion;

    public FormInput(FormVersion formVersion) {
        this.formVersion = formVersion;
    }

    public FormInput() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public FormVersion getFormVersion() {
        return formVersion;
    }

    public void setFormVersion(FormVersion formVersion) {
        this.formVersion = formVersion;
    }

}
