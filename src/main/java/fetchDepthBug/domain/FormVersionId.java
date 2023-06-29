package fetchDepthBug.domain;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class FormVersionId implements Serializable {

    @ManyToOne
    @JoinColumn(name = "form_id")
    private Form form;

    private Integer versionNumber;

    public Form getForm() {
        return form;
    }

    public void setForm(Form form) {
        this.form = form;
    }

    public Integer getVersionNumber() {
        return versionNumber;
    }

    public void setVersionNumber(Integer versionNumber) {
        this.versionNumber = versionNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FormVersionId)) {
            return false;
        }
        FormVersionId that = (FormVersionId) o;
        return form.getId().equals(that.form.getId()) && versionNumber.equals(that.versionNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(form, versionNumber);
    }
}
