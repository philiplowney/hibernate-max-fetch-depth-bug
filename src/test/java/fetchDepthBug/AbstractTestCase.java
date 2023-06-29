package fetchDepthBug;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import fetchDepthBug.domain.*;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import static org.junit.Assert.assertNotNull;

public abstract class AbstractTestCase {
    private Program program;
    private Step step;
    private EntityManager entityManager;

    protected abstract String getEntityManagerName();

    @Before
    public void init() throws IOException {
        Properties properties = new Properties();
        properties.load(AbstractTestCase.class.getClassLoader().getResourceAsStream("database.properties"));
        entityManager = Persistence.createEntityManagerFactory(getEntityManagerName(), properties).createEntityManager();

        entityManager.getTransaction().begin();
        program = new Program();
        entityManager.persist(program);

        Form form = new Form();
        entityManager.persist(form);

        FormVersion formVersion = new FormVersion(form, 1);
        formVersion.getInputs().add(new FormInput(formVersion));

        entityManager.persist(formVersion);

        step = new Step();
        step.setProgram(program);
        StepConfiguration configuration = new StepConfiguration();
        configuration.setStep(step);

        step.setConfiguration(configuration);

        FormOption formOption = new FormOption();
        formOption.setOrder(1);
        formOption.setConfiguration(step.getConfiguration());
        formOption.setFormVersion(formVersion);
        Set<FormOption> formOptions = new HashSet<>();
        formOptions.add(formOption);
        step.getConfiguration().setFormOptions(formOptions);

        entityManager.persist(step);
        entityManager.getTransaction().commit();
        entityManager.clear();
    }

    @After
    public void after() {
        entityManager.getTransaction().begin();
        entityManager.createQuery("DELETE from FormOption").executeUpdate();
        entityManager.createQuery("DELETE from StepConfiguration").executeUpdate();
        entityManager.createQuery("DELETE from Step").executeUpdate();
        entityManager.createQuery("DELETE from FormInput").executeUpdate();
        entityManager.createQuery("DELETE from FormVersion").executeUpdate();
        entityManager.createQuery("DELETE from Form").executeUpdate();
        entityManager.createQuery("DELETE from Program").executeUpdate();
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    @Test
    public void formVersionLoadTest() {
        Query fetchQuery = entityManager.createQuery("SELECT s FROM Step s WHERE s.id = :id");
        fetchQuery.setParameter("id", step.getId());
        Step fetchedStep = (Step) fetchQuery.getSingleResult();

        StepConfiguration configuration = fetchedStep.getConfiguration();
        assertNotNull(configuration.getFormOptions());
        Assert.assertEquals(1, configuration.getFormOptions().size());
        configuration.getFormOptions().forEach(formOption -> {
            FormVersion fv = formOption.getFormVersion();
            assertNotNull(fv);
        });
    }

    @Test
    public void formInputsLoadTest() {
        Query fetchQuery = entityManager.createQuery("SELECT s FROM Step s WHERE s.id = :id");
        fetchQuery.setParameter("id", step.getId());
        Step fetchedStep = (Step) fetchQuery.getSingleResult();

        StepConfiguration configuration = fetchedStep.getConfiguration();
        assertNotNull(configuration.getFormOptions());
        Assert.assertEquals(1, configuration.getFormOptions().size());
        configuration.getFormOptions().forEach(formOption -> {
            FormVersion fv = formOption.getFormVersion();
            assertNotNull(fv);
            assertNotNull(fv.getInputs());
        });
    }
}
