package fetchDepthBug.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

import java.util.ArrayList;
import java.util.List;

@Entity
public class FormVersion {

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "formVersion")
    private List<FormInput> inputs = new ArrayList<>();
//
//    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "formVersion")
//    private List<FormElement> elements = new ArrayList<>();

    @EmbeddedId
    protected FormVersionId id;

    public FormVersionId getId() {
        return id;
    }

    public void setId(FormVersionId id) {
        this.id = id;
    }

    public FormVersion() {
        id = new FormVersionId();
    }

    public FormVersion(Form form, int version) {
        this();
        this.id.setForm(form);
        this.id.setVersionNumber(version);
    }
//
//    public List<FormElement> getElements() {
//        return elements;
//    }
//
//    public void setElements(List<FormElement> elements) {
//        this.elements = elements;
//    }

    public List<FormInput> getInputs() {
        return inputs;
    }

    public void setInputs(List<FormInput> inputs) {
        this.inputs = inputs;
    }

}
