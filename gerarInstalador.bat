taskkill /im explorer.exe

cd launch4j
Launch4j\launch4jc.exe configuracao2.xml
echo "exe gerado com sucesso"
cd ..
InnoSetup5\ISCC.exe instalador\instalador32.iss
explorer instalador
pause