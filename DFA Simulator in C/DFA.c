#include <stdio.h>
#include <stdlib.h>
#include<string.h>
typedef struct  automate automate;
struct automate{


    int nbrL;//nombre de lletre/
    char t[100];//tableaux des lettres//
    int nbrt;//le nombre s de les etats//
    int tt[100];//tableaux de les etats//
    int nbrinit;//nombre de les etats initiale//
    int ttt[100];//tableaux de les etats initiale//
    int nbracc;//nombre de les etats acceptanes//
    int tttt[100];//tableaux de les etat acceptanes//
    int mat[100][100];//matrice de transition contient les etat et le lettre//

};
void read(automate*Automate){
    int i,j;

    do{
    printf("donner le nombre de lettre"); //inserer le nombre de lettres//
    scanf("%d",&Automate->nbrL);
    }while(Automate->nbrL>100);
     do{
    printf("donner le nombre de les etats");//inserer le nombre de les etats//
   scanf("%d",&Automate->nbrt);
    }while(Automate->nbrt>100);
     do{
    printf("donner le nombre de les etats initial "); //inserer le nombre de les etats initial//
    scanf("%d",&Automate->nbrinit);
    }while(Automate->nbrinit>100);
     do{
    printf("donner le nombre de les etats acceptanes"); //inserer le nombre de les etats acceptanes//
    scanf("%d",&Automate->nbracc);
    }while(Automate->nbracc>100);
    for( i=0;i<Automate->nbrL;i++){
        printf("donner la lettre%d\n",i+1); //inserer les lettres//
        scanf("%s",&Automate->t[i]);



    }
    for(i=0;i<Automate->nbrt;i++){
        printf("donner le etat%d\n",i+1);  //inserer les etats //
        scanf("%d",&Automate->tt[i]);



    }
     for( i=0;i<Automate->nbrinit;i++){
        printf("donner le etat initiale%d\n",i+1); //inserer les etats initiale //
        scanf("%d",&Automate->ttt[i]);



    }
     for(i=0;i<Automate->nbracc;i++){
        printf("donner le etat accepter%d\n",i+1); //inserer les etats accpeter //
        scanf("%d",&Automate->tttt[i]);



    }
    for(i=0;i<Automate->nbrt;i++){
        for(j=0;j<Automate->nbrL;j++){

            printf("matrice de transition[%d][%d]\n",i+1,j+1); //inserer les transition //
            scanf("%d",&Automate->mat[i][j]);

        }

    }









}
void write(automate*Automate){
    int i,j;


    for(i=0;i<Automate->nbrt;i++){
        printf(" le etat%d:%d\n",i+1,Automate->tt[i]); // afficher les etats//



    }
     for( i=0;i<Automate->nbrinit;i++){
        printf("le etat initiale%d:%d\n\n",i+1,Automate->ttt[i]); // afficher les etats initiale//




    }
     for(i=0;i<Automate->nbracc;i++){
        printf("le etat accepter%d:%d\n",i+1,Automate->tttt[i]); // afficher les etats accepter//




    }
    for(i=0;i<Automate->nbrt;i++){
        for(j=0;j<Automate->nbrL;j++){

            printf("\n\nmatrice de transition[%d][%d]\n\n\n=%d",i+1,j+1,Automate->mat[i][j]); // afficher les transitions//


        }

    }

}
void test_word(automate Automate){
    char lettre[50];
     int bol = 0, commence = 0, i = 0, E_C, L_C;
printf("\nsaisir la lettre qui vous voulez tester:"); //inseretion de la lettre qui vous voulez tester//
scanf("%s",lettre);
commence=0;
while(i<Automate.nbrL && commence==0){     // tester d'abord si la lettre est dans la table de lettre pour commecer//
     if (lettre[0] == Automate.t[i]){
        commence=1;
    }

i=i+1;

}

if(commence==1){  //si la lettre est dans le tableux de lettre alors on comemnce //







int j=0;
i=0;
while(i<Automate.nbrt){
        j=0;
    while(j<Automate.nbrL){
        E_C=Automate.mat[i][j]; //affecter la premier transition aux etat_current est mis a jourer a chaque fois(parcourire dans les transition)//
        j=j+1;//parcourire le nombre de lettre//
        L_C=Automate.t[j];//affecter la premier lettre aux lettre_current de l'etati est mis a jourer a chaque fois(parcourire dans la table de lettre)//
         E_C=Automate.tt[i];//affecter le premier etat aux etat_current est mis a jourer a chaque fois (parcourire dans la table de les etats)//
    }
    i=i+1;//parcourire le nombre de les etats//
   L_C=Automate.t[j];//affecter la permier lettre aux lettre curent de l'etats i(incremnter l'etat a chaque fois//
         E_C=Automate.tt[i];//affeter l'etat suivnat a l'etat curent//

}

bol=0;
i=0;
while(i<Automate.nbracc && bol==0){
    if(E_C==Automate.tttt[i]){
 printf("vrai");//si on trouve l'etat curent dans les etats accepter alors vrai//
   bol=1;

}
else{

  i=i+1;
}
}}
else{
     printf("\nla lettre n'appartient pas a le tab de lettre\n");
}

if(bol==0){
          printf("faux");//si on ne trouve pas l'etat curent dans les etats accepter alors faux//

}





}


int main()
{
    automate Automate;
    printf("saisir l'automate:\n");
 read(&Automate);
 printf("l'automate est:\n\n\n");
 write(&Automate);
 test_word(Automate);

    return 0;
}

