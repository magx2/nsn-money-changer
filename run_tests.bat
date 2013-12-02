java -jar nsn.jar 1024 NsnMoneyChangerWithoutCache 1000 NsnMoneyChangerWithoutCache.plt
java -jar nsn.jar 1024 NsnMoneyChangerWithCache 1000 NsnMoneyChangerWithCache.plt
java -jar nsn.jar 1024 NsnMoneyChangerWithMapCache 1000 NsnMoneyChangerWithMapCache.plt
java -jar nsn.jar 1024 NsnMoneyChangerWithForkJoin 1000 NsnMoneyChangerWithForkJoin.plt

gnuplot\bin\gnuplot.exe gnuplot_plot.data
pause