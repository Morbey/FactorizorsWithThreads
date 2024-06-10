param (
    [string]$ProjectPath
)

if (-not $ProjectPath) {
    Write-Host "Usage: .\CreateProject.ps1 <project-path>"
    exit 1
}

# Obter o nome do projeto a partir do caminho
$ProjectName = (Split-Path $ProjectPath -Leaf).ToLower()

# Caminho do template do projeto
$TemplatePath = "E:\Programming\Projects\BaseProject"

# Copiar o template para o novo diretório do projeto
Copy-Item -Recurse -Path $TemplatePath -Destination $ProjectPath

# Navegar para o diretório do novo projeto
Set-Location $ProjectPath

# Atualizar o `artifactId` no `pom.xml`
(Get-Content pom.xml) -replace '<artifactId>MyBrandNewTest</artifactId>', "<artifactId>$ProjectName</artifactId>" | Set-Content pom.xml

Write-Host "Project $ProjectName has been created successfully at $ProjectPath."