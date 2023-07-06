package fetchDepthBug.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

import java.util.ArrayList;
import java.util.List;

@Entity
public class FormVersion {

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


}
