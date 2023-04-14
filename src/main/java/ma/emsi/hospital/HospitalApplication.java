package ma.emsi.hospital;

import ma.emsi.hospital.entities.*;
import ma.emsi.hospital.repositories.ConsultationRepository;
import ma.emsi.hospital.repositories.MedecinRepository;
import ma.emsi.hospital.repositories.PatientRepository;
import ma.emsi.hospital.repositories.RendezVousRepository;
import ma.emsi.hospital.service.IHospitalService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

@SpringBootApplication
public class HospitalApplication  {

	public static void main(String[] args) {
		SpringApplication.run(HospitalApplication.class, args);
	}
	@Bean //methode qui va s'executer au demarrage et qui retourne un objet
	CommandLineRunner start(IHospitalService hospitalService,PatientRepository patientRepository,RendezVousRepository rendezVousRepository,MedecinRepository medecinRepository){
		return args -> {
			//inserer des patients
			Stream.of("mohammed","hassan","najat")
					.forEach(name->{
						Patient patient=new Patient();
						patient.setNom(name);
						patient.setDatedateNaissance(new Date());
						patient.setMalade(false);
						hospitalService.savePatient(patient);
					});
			//inserer des medecins
			Stream.of("ayman","hanane","yasmine")
					.forEach(name->{
						Medecin medecin=new Medecin();
						medecin.setNom(name);
						medecin.setEmail(name+"@gmail.com");
						medecin.setSpecialite(Math.random()>0.5?"cardio":"Dentiste");
						hospitalService.saveMedecin(medecin);
					});


			Patient patient=patientRepository.findById(1L).orElse(null);
			Patient patient1=patientRepository.findByNom("mohammed");

			Medecin medecin=medecinRepository.findByNom("yasmine");

			//inserer un rendez-vous

			RendezVous rendezVous=new RendezVous();
			rendezVous.setDate(new Date());
			rendezVous.setStatus(StatusRDV.PENDING);
			rendezVous.setMedecin(medecin);
			rendezVous.setPatient(patient);
			hospitalService.saveRDV(rendezVous);

			RendezVous rendezVous1=rendezVousRepository.findAll().get(0);

			//inserer des consultations

			Consultation consultation=new Consultation();
			consultation.setDateConsultation(new Date());
			consultation.setRendezVous(rendezVous1);
			consultation.setRapport("Rapport de la consultation");
			hospitalService.saveConsultation(consultation);



		};
	}

}
