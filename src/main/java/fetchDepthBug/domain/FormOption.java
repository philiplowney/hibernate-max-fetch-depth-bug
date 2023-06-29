package fetchDepthBug.domain;

import jakarta.persistence.*;

@Entity
public class FormOption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="\"order\"")
    private Integer order;

    @ManyToOne(optional = false)
    @JoinColumn(name = "stepConfiguration_id", foreignKey = @ForeignKey(name = "FK_FormOption_StepConfiguration"))
    private StepConfiguration configuration;

    @ManyToOne(optional = false)
    @JoinColumns(foreignKey = @ForeignKey(name = "FK_FormOption_FormVersion"), value = {
        @JoinColumn(name = "form_id", updatable = false),
        @JoinColumn(name = "versionNumber", updatable = false)
    })
    private FormVersion formVersion;

    public FormOption() {
    }

    public FormOption(FormVersion formVersion) {
        this.formVersion = formVersion;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public StepConfiguration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(StepConfiguration configuration) {
        this.configuration = configuration;
    }

    public FormVersion getFormVersion() {
        return formVersion;
    }

    public void setFormVersion(FormVersion formVersion) {
        this.formVersion = formVersion;
    }
}
