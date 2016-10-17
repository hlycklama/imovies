package ch.eth.infsec;

import ch.eth.infsec.services.pki.CAService;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import javax.annotation.PostConstruct;
import java.security.Security;

@SpringBootApplication
public class IMoviesApplication {

	public static void main(String[] args) {
		SpringApplication.run(IMoviesApplication.class, args);
	}

	@Autowired
	CAService caService;

	@PostConstruct
	public void addBCProvider() {
		Security.addProvider(new BouncyCastleProvider());
	}

	//@PostConstruct
	public void generateCA() {


		System.out.print(Security.getProviders());
		//CAService.Identity identity = caService.getSigningIdentity();

		//System.out.print(identity);
	}

}
