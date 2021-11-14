void bolas_em_queda() {  

  //bolas a cair
  for (int i=0; i<b.length; i++) {
    b[i].forma();

    b[i].movimento();
    b[i].fim_queda();

    b[i].explosao();
  }
}
