package fetchDepthBug.domain;

import jakarta.persistence.*;

@Entity
public class Step {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "program_id", nullable = false, updatable = false, foreignKey = @ForeignKey(name = "FK_Step_Program"))
    private Program program;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Program getProgram() {
        return program;
    }

    public void setProgram(Program program) {
        this.program = program;
    }

    @OneToOne(mappedBy = "step", cascade = CascadeType.ALL)
    private StepConfiguration configuration;

    public StepConfiguration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(StepConfiguration configuration) {
        this.configuration = configuration;
    }
}
