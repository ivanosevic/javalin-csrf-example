<#-- @ftlvariable name="wasNoteCreated" type="java.lang.Boolean" -->
<#-- @ftlvariable name="notes" type="java.util.List<edu.pucmm.eict.javalincsrf.notes.Note>" -->
<#-- @ftlvariable name="csrfToken" type="java.lang.String" -->
<!doctype html>
<html lang="en">
<head>
  <meta charset="UTF-8"/>
  <meta name="viewport"
        content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0"/>
  <meta http-equiv="X-UA-Compatible" content="ie=edge"/>
  <title>My notes</title>
  <link rel="stylesheet" href="/webjars/bootstrap/5.2.2/css/bootstrap.min.css"/>
  <script src="/webjars/bootstrap/5.2.2/js/bootstrap.bundle.min.js" defer></script>
  <script src="/webjars/axios/1.1.2/dist/axios.min.js" defer></script>
</head>
<body>
<div id="root">
  <header class="mb-5 sticky-top">
    <nav class="navbar bg-light p-3">
      <div class="container-fluid">
        <a class="navbar-brand" href="/notes">My Notes</a>
        <div class="ms-auto">
          <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#add-note-modal">
              <#-- Icon for Plus Note -->
            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" fill="currentColor"
                 class="bi bi-file-earmark-plus" viewBox="0 0 16 16">
              <path d="M8 6.5a.5.5 0 0 1 .5.5v1.5H10a.5.5 0 0 1 0 1H8.5V11a.5.5 0 0 1-1 0V9.5H6a.5.5 0 0 1 0-1h1.5V7a.5.5 0 0 1 .5-.5z"></path>
              <path d="M14 4.5V14a2 2 0 0 1-2 2H4a2 2 0 0 1-2-2V2a2 2 0 0 1 2-2h5.5L14 4.5zm-3 0A1.5 1.5 0 0 1 9.5 3V1H4a1 1 0 0 0-1 1v12a1 1 0 0 0 1 1h8a1 1 0 0 0 1-1V4.5h-2z"></path>
            </svg>
            Add note
          </button>
        </div>
      </div>
    </nav>
  </header>

    <#if wasNoteCreated?? >
      <div class="container note-created mb-3">
        <div class="alert alert-success alert-dismissible fade show text-center" role="alert">
          <span class="text-center">Note created successfully</span>
          <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        </div>
      </div>
    </#if>


  <main class="notes container">
      <#list notes as note>
        <div class="row container-fluid mb-3">
          <div class="col-12">
            <section class="note card p-2">
              <div class="card-body">
                <h5 class="card-title">${note.title()}</h5>
                <p class="card-text">${note.message()}</p>
              </div>
            </section>
          </div>
        </div>
      </#list>
  </main>

    <#-- Modal assigned for creating notes -->
  <div class="modal fade" id="add-note-modal" tabindex="-1" aria-labelledby="add-note-modal-label" aria-hidden="true">
    <div class="modal-dialog">
      <div class="modal-content">
        <div class="modal-header">
          <h1 class="modal-title fs-5" id="add-note-modal-label">Add note</h1>
          <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
        </div>
        <div class="modal-body">
          <form id="add-note-form" action="/notes/new" method="post" accept-charset="UTF-8">
            <div class="mb-3">
              <label for="note-title" class="form-label">Title</label>
              <input type="text" class="form-control" id="note-title" name="noteTitle" minlength="1" maxlength="30"
                     required/>
            </div>
            <div class="mb-3">
              <label for="note-message" class="form-label">Message</label>
              <textarea class="form-control" id="note-message" name="noteMessage" rows="6" minlength="1" maxlength="200"
                        required></textarea>
            </div>
            <input type="hidden" name="_csrf" value="${csrfToken}"/>
          </form>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
          <button form="add-note-form" type="submit" class="btn btn-primary">Save</button>
        </div>
      </div>
    </div>
  </div>
</div>
</body>
</html>