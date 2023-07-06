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
import java.util.List;
import java.util.Properties;
import java.util.Set;

import static org.junit.Assert.assertNotNull;

public abstract class AbstractTestCase {

    private StepConfiguration stepConfiguration;

    private FormOption formOption;

    private EntityManager entityManager;

    protected abstract String getEntityManagerName();

    @Before
    public void init() throws IOException {
        Properties properties = new Properties();
        properties.load(AbstractTestCase.class.getClassLoader().getResourceAsStream("database.properties"));
        entityManager = Persistence.createEntityManagerFactory(getEntityManagerName(), properties).createEntityManager();

        entityManager.getTransaction().begin();

        Form form = new Form();
        entityManager.persist(form);

        FormVersion formVersion = new FormVersion(form, 1);

        entityManager.persist(formVersion);

        stepConfiguration = new StepConfiguration();

        formOption = new FormOption();
        formOption.setConfiguration(stepConfiguration);
        formOption.setFormVersion(formVersion);
        Set<FormOption> formOptions = new HashSet<>();
        formOptions.add(formOption);
        stepConfiguration.setFormOptions(formOptions);

        entityManager.persist(stepConfiguration);
        entityManager.getTransaction().commit();
        entityManager.clear();
    }

    @After
    public void after() {
        entityManager.getTransaction().begin();
        entityManager.createQuery("DELETE from FormOption").executeUpdate();
        entityManager.createQuery("DELETE from StepConfiguration").executeUpdate();
        entityManager.createQuery("DELETE from FormVersion").executeUpdate();
        entityManager.createQuery("DELETE from Form").executeUpdate();
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    @Test
    public void formVersionLoadTest() {
        Query fetchQuery = entityManager.createQuery("SELECT s FROM StepConfiguration s WHERE s.id = :id");
        fetchQuery.setParameter("id", stepConfiguration.getId());
        StepConfiguration configuration = (StepConfiguration) fetchQuery.getSingleResult();

        assertNotNull(configuration.getFormOptions());
        Assert.assertEquals(1, configuration.getFormOptions().size());
        configuration.getFormOptions().forEach(formOption -> {
            FormVersion fv = formOption.getFormVersion();
            assertNotNull(fv);
        });
    }

    @Test
    public void formOptionsLoadTest() {
        Query fetchQuery = entityManager.createQuery("SELECT fo FROM FormOption fo");
        List<FormOption> fos = (List<FormOption>) fetchQuery.getResultList();
        Assert.assertEquals(1, fos.size());
        fos.forEach(formOption -> {
            FormVersion fv = formOption.getFormVersion();
            assertNotNull(fv);
        });
    }

    @Test
    public void singleFormOptionLoadTest() {
        Query fetchQuery = entityManager.createQuery("SELECT fo FROM FormOption fo WHERE fo.id = :id");
        fetchQuery.setParameter("id", formOption.getId());
        FormOption fo = (FormOption) fetchQuery.getSingleResult();
        FormVersion fv = fo.getFormVersion();
        assertNotNull(fv);
    }
}
