package ch.eth.infsec.services.pki;

import ch.eth.infsec.model.User;

import java.security.cert.X509Certificate;
import java.util.Collection;

public interface PKIService {

    /**
     * Issues a new certificate for the given user.
     * @param user User object.
     * @param password User-provided password to encrypt the identity with
     * @param caIdentity Signing identity, leave null to use default
     * @return String path to p12 file
     */
    String issueCertificate(User user, String password, CAService.Identity caIdentity);

    /**
     * Revoke a certificate.
     * @param user owner
     * @return whether it succeeded (it existed)
     */
    boolean revokeCertificate(User user);

    /**
     * Determine whether the given certificate is valid, i.e. it has been issued by us and has not been revoked.
     * @param certificate User certificate
     * @return true for valid, false invalid
     */
    boolean isValid(X509Certificate certificate);

    /**
     * Get the number of issued certificates.
     * @return number of issued certificates
     */
    int numberOfCertificates();

    /**
     * Get the number of revoked certificates.
     * @return number of revoked certificates
     */
    int numberOfCRL();

    /**
     * Calculate the current certificate serial number for our CA (based on previous certificates).
     * @return serial number
     */
    int currentSerialNumber();


    Collection<X509Certificate> getAllCertificates();

}
