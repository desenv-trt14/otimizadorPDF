rem cd C:\Users\developer\Documents\NetBeansProjects\conversorpdf
taskkill /im explorer.exe /f
cd launch4j
Launch4j\launch4jc.exe configuracao_64.xml
echo "exe gerado com sucesso"
cd ..
InnoSetup5\ISCC.exe instalador\instalador64.iss
explorer instalador
pause