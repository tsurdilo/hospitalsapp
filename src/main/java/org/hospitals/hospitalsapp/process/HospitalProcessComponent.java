package org.hospitals.hospitalsapp.process;

import java.util.Collections;

import org.hospitals.hospitalsapp.data.Hospital;
import org.hospitals.hospitalsapp.data.Patient;
import org.kie.kogito.Application;
import org.kie.kogito.Model;
import org.kie.kogito.process.Process;
import org.kie.kogito.process.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HospitalProcessComponent {

    @Autowired
    Application kogitoApp;

    public ProcessInstance<?> getNewHospitalProcess(Hospital hospital) {
        Process<? extends Model> p = kogitoApp.processes().processById("newhospital");

        Model m = p.createModel();
        m.fromMap(Collections.singletonMap("hospital",
                                           hospital));

        ProcessInstance<?> processInstance = p.createInstance(m);

        return processInstance;
    }

    public void startNewHospitalProcess(Hospital hospital) {
        getNewHospitalProcess(hospital).start();
    }

    public ProcessInstance<?> getNewPatientProcess(Patient patient) {
        Process<? extends Model> p = kogitoApp.processes().processById("newpatient");

        Model m = p.createModel();
        m.fromMap(Collections.singletonMap("patient",
                                           patient));

        ProcessInstance<?> processInstance = p.createInstance(m);

        return processInstance;
    }

    public void startNewPatientProcess(Patient patient) {
        getNewPatientProcess(patient).start();
    }
}
