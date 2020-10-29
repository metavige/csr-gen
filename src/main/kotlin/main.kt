import org.spongycastle.openssl.jcajce.JcaPEMWriter
import org.spongycastle.operator.jcajce.JcaContentSignerBuilder
import org.spongycastle.pkcs.jcajce.JcaPKCS10CertificationRequestBuilder
import java.io.OutputStreamWriter
import java.io.PrintStream
import java.security.KeyPair
import java.security.KeyPairGenerator
import javax.security.auth.x500.X500Principal

fun main(args: Array<String>) {
    genCSR(args[0], Integer.parseInt(args[1]))
}

fun genCSR(domain: String, keySize: Int)
{
    // 建立 keyPair generator
    val generator = KeyPairGenerator.getInstance("RSA")
    generator.initialize(keySize);

    // Public/Private Key
    val pair: KeyPair = generator.generateKeyPair()
    val privateKey = pair.private
    val publicKey = pair.public

    // 建立 CSR
    val subject =
        X500Principal("C=TW, ST=Taiwan, L=Taipei, O=Mecuries Life Insurance, OU=IT, CN=${domain}")
    val signGen = JcaContentSignerBuilder("SHA256withRSA").build(privateKey)
    val builder = JcaPKCS10CertificationRequestBuilder(subject, publicKey)

    // build csr
    // build csr
    val csr = builder.build(signGen)
    // 輸出 PEM 格式的 CSR
    output2Pem(csr, System.out)
    // TODO: 輸出 Private Key
}

fun output2Pem(obj: Any, outSteam: PrintStream) {
    val output = OutputStreamWriter(outSteam)
    val pem = JcaPEMWriter(output);
    pem.writeObject(obj);
    pem.flush()
    pem.close()
}