package fr.laerce.gestionstages.service;

import fr.laerce.gestionstages.dao.IndividuRepository;
import fr.laerce.gestionstages.domain.Individu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
public class LoginManager {
    @Autowired
    IndividuRepository repoIndividu;

    @Autowired
    PasswordEncoder passwordEncoder;

    public void createLoginForIndividu(Long id) {
        Optional<Individu> val = repoIndividu.findById(id);
        if(val.isPresent()){
            Individu individu = val.get();
            if(individu.getLogin() == null || individu.getLogin().equals("")) {
                calculLogin(individu);
                individu.setMdpOrigine(this.createPassword());
                individu.setMdp(this.passwordEncoder.encode(individu.getMdpOrigine()));
                individu.setRoles("USER"); // rôle par défaut....
                repoIndividu.save(individu);
            }
        }
    }

    private void calculLogin(Individu individu) {
        StringBuffer nom = new StringBuffer(individu.getNom().toLowerCase());
        strip(nom);
        String prenom = individu.getPrenom().toLowerCase().substring(0,1);
        String loginBase = prenom+nom.toString().substring(0,Integer.min(nom.length(),7));
        //stripWord(loginBase);
        individu.setLogin(loginBase);
        boolean loginOk = false;
        int count = 1;
        do {
            Individu ind = repoIndividu.findByLogin(individu.getLogin());
            if(ind != null) {
                individu.setLogin(loginBase+count);
                count++;
            } else {
                loginOk = true;
            }
        } while (!loginOk);
    }

    private void strip(StringBuffer nom) {
        for(int i = 0; i < nom.length(); ){
            switch (nom.charAt(i)) {
                case ' ':
                case '\'':
                case '-':
                case '_':
                    nom.deleteCharAt(i);
                    break;
                default:
                    i++;
                    break;
            }
        }
    }

    private void stripWord(String loginBase) {
        for(int i = 0; i < loginBase.length(); ){
            switch (loginBase.charAt(i)) {
                case 'é':
                case 'è':
                case 'ê':
                case 'ë':
                    loginBase.replace(loginBase.charAt(i),"e".charAt(0));
                case 'à':
                case 'â':
                case 'ä':
                    loginBase.replace(loginBase.charAt(i),"a".charAt(0));
                case 'î':
                case 'ï':
                    loginBase.replace(loginBase.charAt(i),"i".charAt(0));
                case 'ö':
                case 'ô':
                    loginBase.replace(loginBase.charAt(i),"o".charAt(0));
                case 'ù':
                case 'û':
                case 'ü':
                    loginBase.replace(loginBase.charAt(i),"u".charAt(0));
                    break;
                default:
                    i++;
                    break;
            }
        }
    }

    private String createPassword(){
        StringBuffer password = new StringBuffer();
        /*String consonnes = "bcdfghjklmnpqrstvwxz";
        String voyelles = "aeiouy";
        Random alea = new Random();
        for(int i = 0; i <6; i++){
            if(i%2 == 0){
                password.append(consonnes.charAt(alea.nextInt(consonnes.length())));
            } else {
                password.append(voyelles.charAt(alea.nextInt(voyelles.length())));
            }
        }
        for(int i = 0; i < 4; i++){
            password.append(alea.nextInt(9));
        }*/
        String alphabet = "abcdefghijklmnopqrstuvwxyz";
        String voyelle = "aeiouy";
        String consonne = "zrtpqsdfghjklmwxcvbn";
        String nombre = "0123456789";

        int randomAlpha = (int) (Math.random() * (26));
        String first = "" + alphabet.charAt(randomAlpha);
        //System.out.println(first);
        password.append(first.toUpperCase());
        if (voyelle.contains(first)) {
            //System.out.println("voyelle");
            boolean voy = false;
            for (int i = 2; i <= 6; i++) {
                int randomVoy = (int) (Math.random() * (6));
                int randomCons = (int) (Math.random() * (20));
                if (!voy) {
                    password.append(consonne.charAt(randomCons));
                    voy = true;
                } else {
                    password.append(voyelle.charAt(randomVoy));
                    voy = false;
                }
            }
        }
        if (consonne.contains(first)) {
            //System.out.println("consonne");
            boolean cons = false;
            for (int i = 2; i <= 6; i++) {
                int randomVoy = (int) (Math.random() * (6));
                int randomCons = (int) (Math.random() * (20));
                if (!cons) {
                    password.append(voyelle.charAt(randomVoy));
                    cons = true;
                } else {
                    password.append(consonne.charAt(randomCons));
                    cons = false;
                }
            }
        }

        for (int i = 1; i <= 4; i++) {
            int randomNb = (int) (Math.random() * (10));
            password.append(nombre.charAt(randomNb));
        }

        return password.toString();
    }
}

