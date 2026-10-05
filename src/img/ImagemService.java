package src.img;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

public class ImagemService {

    public String converterParaBase64(String caminhoArquivo) throws IOException {
        byte[] bytes = Files.readAllBytes(Paths.get(caminhoArquivo));
        return Base64.getEncoder().encodeToString(bytes);
    }

    public void salvarBase64(String base64, String caminhoSaida) throws IOException {
        byte[] imgBytes = Base64.getDecoder().decode(base64);
        Files.write(Paths.get(caminhoSaida), imgBytes);
    }
}