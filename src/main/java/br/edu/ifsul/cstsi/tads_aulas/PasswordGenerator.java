package br.edu.ifsul.cstsi.tads_aulas; // Use o seu pacote base

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordGenerator {
    public static void main(String[] args) {

        String senhaDesejada = "suasenhaforte123";

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String hash = encoder.encode(senhaDesejada);

        System.out.println("------------------------------------");
        System.out.println("Senha (Plaintext): " + senhaDesejada);
        System.out.println("COPIE ESTE VALOR: " + hash);
        System.out.println("------------------------------------");
    }
}