package org.roisu.test.cdi;

import java.util.ArrayList;
import java.util.List;

import org.apache.deltaspike.cdise.api.CdiContainer;
import org.junit.runners.model.MultipleFailureException;
import org.junit.runners.model.Statement;

public class RunAfterTestClassCallbacks extends Statement {
    private final Statement next;
    private final CdiContainer cdiContainer;

    public RunAfterTestClassCallbacks(Statement next, CdiContainer cdiContainer) {
        super();
        this.next = next;
        this.cdiContainer = cdiContainer;
    }

    @Override
    public void evaluate() throws Throwable {
        List<Throwable> errors = new ArrayList<Throwable>();
        try {
            this.next.evaluate();
        } catch (Throwable e) {
            errors.add(e);
        }

        try {
            this.cdiContainer.shutdown();
        } catch (Exception e) {
            errors.add(e);
        }

        if (errors.isEmpty()) {
            return;
        }
        if (errors.size() == 1) {
            throw errors.get(0);
        }
        throw new MultipleFailureException(errors);

    }

}
